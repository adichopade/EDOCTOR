from flask import Flask,request,jsonify,render_template
import pickle
import pandas as pd
import json
from json import JSONEncoder
import numpy

app=Flask(__name__)
model=pickle.load(open('model.pkl','rb'))
data = pd.read_csv('data_pivoted.csv')
data=pd.DataFrame(columns = data.columns[1:])
print(data)

@app.route('/predict',methods=['GET','POST'])
def predict():
    if request.method == "GET":
        return jsonify({"response":"Get Request Called"})
    elif request.method == "POST":
        req_Json = request.json
        ab=req_Json['ab']
        bc=req_Json['bc']
        cde=req_Json['cde']
        de=req_Json['de']
        ef=req_Json['ef']
        fg=req_Json['fg']
        if ab=="none" and ef=="none" and fg=="none" and bc=="none" and cde=="none" and de=="none":
            ab="abdomen acute"
            modD = data.append({ab:0},ignore_index=True )
            ab="none"
        elif ef=="none" and fg=="none" and bc=="none" and cde=="none" and de=="none":
            modD = data.append({ab:1},ignore_index=True )
        elif ef=="none" and fg=="none" and cde=="none" and de=="none":
            modD = data.append({ab:1,bc:1},ignore_index=True )
        elif ef=="none" and fg=="none" and de=="none":
            modD = data.append({ab:1,bc:1,cde:1},ignore_index=True )
        elif ef=="none" and fg=="none":
            modD = data.append({ab:1,bc:1,cde:1,de:1},ignore_index=True )
        elif fg=="none":
            modD = data.append({ab:1,bc:1,cde:1,de:1,ef:1},ignore_index=True )
        else:
            modD = data.append({ab:1,bc:1,cde:1,de:1,ef:1,fg:1},ignore_index=True )
    

        if ab=="none" and ef=="none" and fg=="none" and bc=="none" and cde=="none" and de=="none":
            fina="No Disease"
        else:
            find=modD.fillna(0)
            print(find)
            ans=model.predict(find)
            print(ans)
            ans1=ans.tolist()
            print(ans1)
            fina=json.dumps(ans1)
            print(fina)

        return jsonify({"response": fina})




if __name__ == '__main__':
    app.run(debug=True,port=9090)


