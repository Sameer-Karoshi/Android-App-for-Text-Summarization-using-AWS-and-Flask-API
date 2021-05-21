from flask import Flask
from flask import request, jsonify
from extractive_summarization import Extractive_Summarization
app = Flask(__name__)

@app.route('/', methods=['GET', 'POST'])
def index():
    if request.method == "POST":
        text = request.form['itext']
        output = Extractive_Summarization.run_summarization(text)
        return output
    return jsonify({"message": "AWS"})

@app.route('/android', methods=['GET', 'POST'])
def Android():
    if request.method == "POST":
        input_data = request.get_json()
        print("1")
        if input_data:
            print("2")
            #print(input_data["input_text"])
            output = Extractive_Summarization.run_summarization(input_data['jsonObject']['nameValuePairs']['input_text'])
            print(output)
            return jsonify({"message": output})
        else:
            return jsonify({"message": "No json input"})
    else:
        return jsonify({"message": "No Post Method Found"})

@app.route('/android_1', methods=['GET', 'POST'])
def Android_1():
    if request.method == "POST":
        input_data = request.get_json()
        print(input_data)
        if input_data:
            if "input_text" in input_data:
                #print(input_data["input_text"])
                output = Extractive_Summarization.run_summarization(input_data['input_text'])
                print(output)
                return jsonify({"message": output})
            else:
                return jsonify({"message": "Input text not found!"})
        else:
            return jsonify({"message": "No json input"})
    else:
        return jsonify({"message": "No Post Method Found"})
    

if __name__ == '__main__':
    app.run(host='0.0.0.0',port=8888,debug=False)
