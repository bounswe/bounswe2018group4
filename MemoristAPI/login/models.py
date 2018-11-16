from django.contrib.auth.models import User
from django.db import models


class RegisteredUser(User):
    age = models.IntegerField(null=True)
    activeEmail_status = models.BooleanField(default=False)

    def __str__(self):
        return self.first_name.__str__() + " " + self.last_name.__str__()


class Activation(models.Model):
    user = models.ForeignKey(RegisteredUser, on_delete=models.CASCADE)
    code = models.CharField(max_length=120, null=False)
    created_time = models.DateField(auto_now_add=True, editable=False)
    is_active = models.BooleanField(default=True)

    def __str__(self):
        return self.code + " - " + self.created_time.__str__()
