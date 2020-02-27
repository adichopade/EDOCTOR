from sklearn import metrics
from sklearn import datasets
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from sklearn.neighbors import KNeighborsClassifier
from sklearn.model_selection import train_test_split
from sklearn.naive_bayes import MultinomialNB
import pickle

Symptoms = pd.read_csv('data_pivoted.csv')
Symptoms = Symptoms.fillna(0)

cols = Symptoms.columns.tolist()
cols.remove('disease')
x = Symptoms[cols]
y = Symptoms.disease

x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.33, random_state=42)


mnb_tot = MultinomialNB()
prediction = mnb_tot.fit(x, y)
Accuracy=mnb_tot.score(x, y)
print(Accuracy)


pickle.dump(prediction,open('model.pkl','wb'))

