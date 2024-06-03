import psycopg2
import csv
#establishing the connection

conn = psycopg2.connect(database="postgres", user='postgres', password='123', host='localhost', port= '5433')

#Creating a cursor object using the cursor() method
cursor = conn.cursor()

# Open and read the CSV file
with open('pokemon.csv', mode='r') as csvfile:
    spamreader = csv.DictReader(csvfile, delimiter=',', quotechar='|')
    for row in spamreader:
        # Prepare insert query
        # if (row['type1'] != 'Grass' or row['type1'] != 'Fire' or row['type1'] != 'Water' or row['type1'] != 'Electric' or row['type1'] != 'Normal'):
        #    continue
        
        insert_query = '''
        INSERT INTO pokemon (name, type1, hp, attack, defense)
        VALUES (%s, %s, %s, %s, %s)
        '''
        data = (
            row['name'],
            row['type1'],
            int(row['hp']),
            int(row['attack']),
            int(row['defense']),
        )
        cursor.execute(insert_query, data)

# Commit the transaction
conn.commit()