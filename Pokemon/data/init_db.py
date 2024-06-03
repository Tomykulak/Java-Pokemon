import psycopg2
import csv
#establishing the connection

conn = psycopg2.connect(database="postgres", user='postgres', password='123', host='localhost', port= '5433')

#Creating a cursor object using the cursor() method
cursor = conn.cursor()

with open('pokemon.csv', mode='r') as csvfile:
    spamreader = csv.reader(csvfile, delimiter=',', quotechar='|')
    for row in spamreader:
        print(', '.join(row))
