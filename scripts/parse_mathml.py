from collections import deque
import requests
import json
import environment
import pyrebase

# set up firebase instance
firebase = pyrebase.initialize_app(environment.config)
db = firebase.database()

operands = '' # a stack to hold the contents of the question/answer

def mathMLError(iterator, str):
    if(iterator >= len(str)):
        print("There is an error with the MathML format of this string")
        return True
    return False

def readString(str, operands):
    iterator =  0
    while(iterator < len(str)):
        # check for the starting tag, hopefully there won't be any inequalities :D
        # We will iterate until we find out what the tag is and add or remove the tag from the stack
        if(str[iterator] == '<'):
            operator = ''
            # check for the ending tag
            while(str[iterator] != '>'):
                if(str[iterator] == '/'):
                    if(str[iterator + 1] == ">"):
                        print(mathStack.pop())
                        iterator += 2
                        continue
                # if we are not looking at the ending tag we need to get the rest of the tag and save it
                iterator += 1
                # We need to do a check to make sure that we don't have the ending '>' for the first tag
                if(str[iterator] == ">"):
                    mathStack.append(operator)
                    continue
                operator += str[iterator]
                if(mathMLError(iterator, str)):
                    return
        # TODO: This should contain the numbers and operators which we will deal with later
        else:
            operands = operands + str[iterator]

        # need to account for the fractions
        
        # make sure that we don't have an incompatible string, may need to add additional checks
        if(mathMLError(iterator, str)):
            return
        iterator += 1
    # don't save the starting and ending quotation marks
    operands = operands.replace('\"','')
    return operands

def convertToDivideSign(operands):
    operands = operands.replace('&#xF7;', '/')
    return operands

def convertToMultiplicationSign(operands):
    operands = operands.replace('&Cross;', '*')
    return operands

def convertHexCodes(operands):
    operands = convertToDivideSign(operands)
    operands = convertToMultiplicationSign(operands)
    return operands

# checks our database for the id to make sure we don't store multiple instances
# of the same problem in the database
def checkProblemId(id):
    id_check = db.child("problem").order_by_child("id").equal_to(1).get();
    if(id_check):
        return True
    else:
        return False

# work with json data
def getJsonElement(json_data, elementName):
    convertedJsonData = json.load(json_data)
    return convertedJsonData[elementName]
    
mathStack = deque() # a stack to hold the MathML elements

# Get a response from studycounts API
response = requests.get('https://studycounts.com/api/v1/arithmetic/simple/ybzbfclyj.json',
                        headers=environment.studycounts)
class Problem:
    def __init__(self, id = '', difficulty = '', question ='', answer = 0.0):
        self.id = id
        self.difficulty = difficulty
        self.question = question
        self.answer = answer
    
    def setId(self, id):
        self.id = id
    
    def setDifficulty(self, difficulty):
        self.difficulty = difficulty
    
    def setQuestion(self, question):
        self.question = question
    
    def setAnswer(self, answer):
        self.answer = answer

problem = Problem()
id = response.json()["id"]
print(id)
difficulty = response.json()["difficulty"]
print(difficulty)
instruction =response.json()["instruction"]
question = json.dumps(response.json()["question"])
question = "".join(question.split()); # Want to remove all of the end line and whitespaces in the string
correctChoiceNum = response.json()["correct_choice"]
correctChoice = json.dumps(response.json()["choices"][correctChoiceNum])

operands = readString(question, operands)
print(mathStack)
operands = convertHexCodes(operands)
print(instruction + ': ' + operands)
problem.setId(id)
problem.setDifficulty(difficulty)
problem.setQuestion(instruction + ': ' + operands)
# clear mathstack and operands from the question and do the same thing for the choices
mathStack.clear()
operands = ''
operands = readString(correctChoice, operands)
print(mathStack)
operands = convertHexCodes(operands)
problem.setAnswer(operands)
print(operands)

# Store problem into Firebase Database
problem_data = {
    'id': problem.id,
    'problemDifficulty': problem.difficulty,
    'question': problem.question,
    'answer': problem.answer
    }
# TODO: need to check database for the id of problem before adding it to the database    
if (checkProblemId(getJsonElement(problem_data, 'id'))):
    db.child('problem').push(problem_data)
