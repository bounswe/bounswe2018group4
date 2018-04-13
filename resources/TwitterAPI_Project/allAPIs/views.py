from django.shortcuts import render,HttpResponse
import tweepy
import ssl
import json
from tweepy import OAuthHandler
from keys import *

def retweet(request):
	

	#  get authorization from Twitter API using tweepy

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	api = tweepy.API(auth)

	#  get the values of the user, and the target

	#if 'user_id' in request.POST
	userid=request.POST['user_id']
	# block
	list=api.followers_ids(id=userid)

	return render(request,'followers_ids.html',{'list':list})

def home(request):
	return render(request,'home.html')


def followerid_index(request):
	return render(request,'followerid_index.html')
	# Create your views here.

def get_trending_topics(name):
	"""
		Author: Yunus Ege Saygılı

		Returns top ten trending topics for the input location. If the location is invalid,
		returns the top ten trending topics worldwide.
	"""



	#  get authorization from Twitter API using tweepy
	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	auth.secure = True

	api = tweepy.API(auth)

	places = api.trends_available()
	
	woeid = 1 # woeid for worldwide is 1

	for place in places: # search for the input parameter
		if place['name'] == name: # if found, get the id of the given location
			woeid = place['woeid']
			break

	trends = api.trends_place(woeid) # get the trending topics of the location
	trendlist = []

	for i in range(10) : # get the top ten tending topics
		trendlist.append(trends[0]['trends'][i]['name'])
	return trendlist

def block_user(theUser, theTarget):
	# author: Berke Esmer - 2015400021
	# block_user - params: theTarget user, the user who is logged on


	#  get authorization from Twitter API using tweepy

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)
	api = tweepy.API(auth)

	#  get the values of the user, and the target

	api.create_block(id=theTarget, user_id=theUser)
	
	
def followUser(theUser):
	"""
	Author: Yusuf Baspinar

	Follows given user
	Follows the accounts inside followList.txt that is given as id's.
	"""
	#  get authorization from Twitter API using tweepy

	auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
	auth.set_access_token(access_token, access_token_secret)

	api = tweepy.API(auth)
	
	api.create_friendship(theUser) #Follows given user
	
	followList = [line.strip() for line in open('followList.txt')]

	# Follow everyone from list. If already following users in the list unfollow
	with open('followList.txt') as f:
	   for line in f:
		user_id = line.strip()   
		if api.exists_friendship(981859542555348992, user_id): # 981859542555348992 is the user_id of MemoristBeaver
			api.destroy_friendship(user_id)
		else:
		       api.create_friendship(user_id)

		
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



def getFavorites(userName):
    auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)

    api = tweepy.API(auth)
    result_set = api.favorites(userName)
    for i in range(len(result_set)):
        status = result_set[i]
        print(status._json['text'])
