# author: Dilara Gökay
import tweepy

consumer_key='5GNyqW1qeECVJ6m3dzx6QGD3f'
consumer_secret='6Q7N0yZle5qvbDLqnIioRBXzkqODmSMdKSqxIHevrZKnHyWjkf'

access_token='981859542555348992-4VIhOkbS4MJru964CPgrGFteVQIzj1p'
access_token_secret='sUaIhvHW91KMgI1EqWijJzcbMMm6BLoSNd9HU76FCB4Go'

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)
api = tweepy.API(auth)

def retweet_last_tweet(user):
	api.retweet(api.user_timeline(user)[0].id)

def retweet(id):
	api.retweet(id)

retweet_last_tweet('bogazici_cmpe')