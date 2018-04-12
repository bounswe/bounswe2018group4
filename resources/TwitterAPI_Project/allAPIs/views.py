from django.shortcuts import render,HttpResponse
import tweepy
from tweepy import OAuthHandler

def retweet(request):
	consumer_key='LtUoLhvWzGOUODCpHkIW1cH7s'
	consumer_secret='o18cdHxf8YLgLaWJZZ8lMvcv2SACOAIJYZmZB5s7nxmPPHRgc3'

	access_token='984099024402894848-bTCwMxW8o04woXa6OAD2Y7PIoxy3TPb'
	access_token_secret='ENb06BGdQamMnFHaxqDeUFL8GzafGIylruSW0s3ubXgqs'

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

def view_followers(screen_name):
    """AUTHOR:Yunus Emre Demirci
    View followers user for given screen_name
    key-words: screen_name;  example:canberky
    return followers information and printing screen_name
    """
    result_set_followers=api.followers(screen_name)
    for i in range(len(result_set_followers)):
        status=result_set_followers[i]
        print("followers"+str(i)+" "+status._json['screen_name'])
    return result_set_followers

def view_following(screen_name):
    """AUTHOR:Yunus Emre Demirci
    View following user name for given screen_name
    key-words: screen_name;  example:canberky_ 
    return following information and printing screen_name
    """
    result_set_following=api.friends(screen_name)
    for i in range(len(result_set_following)):
        status=result_set_following[i]
        print("following"+str(i)+ " "+status._json['screen_name'] )
    return result_set_following
