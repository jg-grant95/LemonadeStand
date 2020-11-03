To handle the mathly API it gives me the ID for the response, the question, the choices, the correct choice, the instruction, the category, the topic, and the difficulty. 
I will need to get the id of the response, get the instruction and append the question (parse through the MathML of the question to get the actual string of the question), parse through the correct choice (I can simplify by parsing through the correct choice), and then saving the instruction, and convert the difficulty to what I have previously used in my Android app already.
Since I am limited to the amount (right now 10 requests/mintue - I'll probably run this a couple times a day to make sure I'm not doing anything unethical) of times I can call the API (since the website has been bought by another party), I need to create a crontab or some other schedule tasked to query this website as many times as I can and store the unique responses into the database of questions that I have

I'll need to access the database for python and connect it to my Android application. I've never really used firebase with Python so I'll check the documentation for how to do this or try to see if the there is a third party library that I can utilize to store the data that I need to store. (Looks like they have just that on the website - https://firebase.google.com/docs/database/rest/start)


Examples of Presentation MathML
string = """<mrow>
    <mrow>
      <mi>x</mi>
      <mo>+</mo>
      <mi>y</mi>
    </mrow>
    <mo>=</mo>
    <mn>2</mn>
  </mrow>"""

  Todo:
  12/18 - Now that I have the basic implementation for how to add a problem to the firebase database, I need to clean up the code to make it a little easier to read and I also need to check the database to make sure that the same question is not stored in the database again. I may restructure the data to 