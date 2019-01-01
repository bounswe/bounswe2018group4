from django.contrib.auth.models import User
from django.db import models

GENDER = (
    (1, "Male"),
    (2, "Female"),
    (3, "Other/Don't want to say")
)


class PointLocation(models.Model):
    location_name = models.CharField(max_length=30, null=False, blank=False)
    location_coordinate_latitude = models.CharField(max_length=30, null=False, blank=False)
    location_coordinate_longitude = models.CharField(max_length=30, null=False, blank=False)


class RegisteredUser(User):
    age = models.IntegerField(null=True)
    activeEmail_status = models.BooleanField(default=False)
    gender = models.IntegerField(choices=GENDER, null=True, blank=True)
    location = models.CharField(null=True, blank=True, max_length=30)
    advanced_location = models.ForeignKey(PointLocation, null=True, blank=True, on_delete=models.CASCADE)

    def __str__(self):
        return self.first_name.__str__() + " " + self.last_name.__str__()


class Activation(models.Model):
    user = models.ForeignKey(RegisteredUser, on_delete=models.CASCADE)
    code = models.CharField(max_length=120, null=False)
    created_time = models.DateField(auto_now_add=True, editable=False)
    is_active = models.BooleanField(default=True)

    def __str__(self):
        return self.code + " - " + self.created_time.__str__()


class ProfilePhoto(models.Model):
    user = models.ForeignKey(RegisteredUser, on_delete=models.CASCADE)
    image = models.FileField(upload_to='profile_photos')

    def __str__(self):
        return self.user.__str__() + " - " + self.image.__str__()


class Follow(models.Model):
    follower = models.ForeignKey(RegisteredUser, on_delete=models.CASCADE, related_name="follower")
    followed = models.ForeignKey(RegisteredUser, on_delete=models.CASCADE, related_name="followed")

    def __str__(self):
        return self.follower.__str__() + " - " + self.followed.__str__()
