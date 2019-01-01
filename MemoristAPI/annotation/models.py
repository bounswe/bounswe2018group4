from login.models import *
from django.db import models


class Creator(models.Model):
    c_id = models.ForeignKey(RegisteredUser, on_delete=models.CASCADE)
    c_type = models.CharField(max_length=50)
    name = models.CharField(max_length=50)
    nickname = models.CharField(max_length=50)


class Selector(models.Model):
    s_type = models.CharField(max_length=50)
    start = models.CharField(max_length=10)
    end = models.CharField(max_length=10)


class Body(models.Model):
    b_type = models.CharField(max_length=50)
    value = models.CharField(max_length=300)


class Target(models.Model):
    t_type = models.CharField(max_length=50)
    source = models.CharField(max_length=70)
    selector = models.ForeignKey(Selector, on_delete=models.CASCADE)


class Annotation(models.Model):
    context = models.CharField(max_length=50)
    a_type = models.CharField(max_length=50)
    motivation = models.CharField(max_length=50)
    creator = models.ForeignKey(Creator, on_delete=models.CASCADE)
    created = models.DateTimeField(auto_now_add=True, editable=False)
    body = models.ForeignKey(Body, on_delete=models.CASCADE)
    target = models.ForeignKey(Target, on_delete=models.CASCADE)
