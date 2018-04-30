# Kadir Aslan 

import tweepy

# Set the 'Consumer Key' and 'Access Tokens'

consumer_key='< CONSUMER KEY >'
consumer_secret='< CONSUMER SECRET >'

access_token='< ACCESS TOKEN >'
access_token_secret='< ACCESS TOKEN SECRET >'

# Get Authorization from Twitter API

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

api = tweepy.API(auth)

# This method provides user ability to change profile name, location and description

def updateProfile():

    while True:
    
        print ("Which information would you like to change?")
    
        print ("1 > Name")
        print ("2 > Location")
        print ("3 > Description")
        print ("0 > Exit")
    
        choice = int(input())
    
        if (choice is 1):
            name = input()
            api.update_profile(name=name)
    
        elif (choice is 2):
        
            location = input()
            api.update_profile(location=location)
    
        elif (choice is 3):
        
            description = input()
            api.update_profile(description=description)
        
        elif (choice is 0):
        
            break;
    
        else:
        
            print("You have entered wrong input, please try again.")
    
