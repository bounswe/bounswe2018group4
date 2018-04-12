# author: Yusuf Baspinar

# pip install tweepy

import tweepy

consumer_key='5GNyqW1qeECVJ6m3dzx6QGD3f'
consumer_secret='6Q7N0yZle5qvbDLqnIioRBXzkqODmSMdKSqxIHevrZKnHyWjkf'

access_token='981859542555348992-4VIhOkbS4MJru964CPgrGFteVQIzj1p'
access_token_secret='sUaIhvHW91KMgI1EqWijJzcbMMm6BLoSNd9HU76FCB4Go'
#  get authorization from Twitter API using tweepy

auth = tweepy.OAuthHandler(consumer_key, consumer_secret)
auth.set_access_token(access_token, access_token_secret)

api = tweepy.API(auth)


followList = [line.strip() for line in open('followList.txt')]

# Follow everyone from list. If already following users in the list unfollow
with open('followList.txt') as f:
   for line in f:
        user_id = line.strip()   
        if api.exists_friendship(981859542555348992, user_id): # 981859542555348992 is the user_id of MemoristBeaver
                api.destroy_friendship(user_id)
        else:
               api.create_friendship(user_id)




