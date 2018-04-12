#author:Yunus Emre Demirci
#the API take given screen_name and list followers and following screen_name user for this user 
import json
import ssl
import time
import tweepy


def view_followers(screen_name):
    """View followers user for given screen_name
    key-words: screen_name;  example:canberky
    return followers information and printing screen_name
    """
    result_set_followers=api.followers(screen_name)
    for i in range(len(result_set_followers)):
        status=result_set_followers[i]
        print("followers"+str(i)+" "+status._json['screen_name'])
    return result_set_followers

def view_following(screen_name):
    """View following user name for given screen_name
    key-words: screen_name;  example:canberky_ 
    return following information and printing screen_name
   """
    result_set_following=api.friends(screen_name)
    for i in range(len(result_set_following)):
        status=result_set_following[i]
        print("following"+str(i)+ " "+status._json['screen_name'] )
    return result_set_following

# set up the consumer key and access token (obtained from https://apps.twitter.com/app/new)

consumer_key='5GNyqW1qeECVJ6m3dzx6QGD3f'
consumer_secret='6Q7N0yZle5qvbDLqnIioRBXzkqODmSMdKSqxIHevrZKnHyWjkf'

access_token='981859542555348992-4VIhOkbS4MJru964CPgrGFteVQIzj1p'
access_token_secret='sUaIhvHW91KMgI1EqWijJzcbMMm6BLoSNd9HU76FCB4Go'

#  get authorization from Twitter API using tweepy

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

screen_name=input("enter screen_name: ")


api = tweepy.API(auth)

view_followers(screen_name)
view_following(screen_name)
