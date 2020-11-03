import requests
import json
import libsbml
import sys

req = requests.get('https://studycounts.com/api/v1/algebra/linear-equations.json')

# print(json.dumps(req.json(), indent=2, sort_keys=True))
print(json.dumps(req.json()["question"]))
mathml_start = """<?xml version="1.0" encoding="UTF-8"?>
    <math xmlns="http://www.w3.org/1998/Math/MathML">"""
my_math_string = json.dumps(req.json()["question"])
mathml_end = "</math>"

test_string = mathml_start + my_math_string + mathml_end;

def navigateMath(node):
    #
    # first take the current element
    #
    if node.getType() == libsbml.AST_PLUS:
        if node.getNumChildren() == 0:
            print ("0")
            return
        navigateMath(node.getChild(0))
        for i in range(1, node.getNumChildren()):
            print ("+")
            navigateMath(node.getChild(i))
    
    elif node.getType() == libsbml.AST_REAL:
        # this will be constants
        print (node.getReal())
    elif node.getType() == libsbml.AST_NAME:
        # this will be ci elements
        print (node.getName())
    else:
        # handle more cases here ...
        pass


if __name__ == "__main__":
    print(libsbml.getLibSBMLDottedVersion())
    mathml_string = """<?xml version="1.0" encoding="UTF-8"?>
        <math xmlns="http://www.w3.org/1998/Math/MathML">
          <apply>
            <plus/>
            <ci> PKC_DAG_AA_p </ci>
            <ci> PKC_Ca_memb_p </ci>
            <ci> PKC_Ca_AA_p </ci>
            <ci> PKC_DAG_memb_p </ci>
            <ci> PKC_basal_p </ci>
            <ci> PKC_AA_p </ci>
            <ci> globalParameter </ci>
            <cn type="integer"> 4 </cn>
          </apply>
        </math>
        """
    # read the mathml 
    mathml = libsbml.readMathMLFromString(test_string)
    
    # if mathml is None:
    #     # bail if we couldn't read it 
    #     print ("Couldn't parse the mathml string, please verify it is correct")
    print(mathml)
    sys.exit(1)

    # navigate all items 
    navigateMath(mathml)
