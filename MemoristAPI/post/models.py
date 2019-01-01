from django.db import models
from login import models as loginmodels
from location_field.models.plain import PlainLocationField

DECADE = (
    (1, "1910s"),
    (2, "1920s"),
    (3, "1930s"),
    (4, "1940s"),
    (5, "1950s"),
    (6, "1960s"),
    (7, "1970s"),
    (8, "1980s"),
    (9, "1990s"),
    (0, "1900s"),
)
MONTH = (
    (1, "Jenuary"),
    (2, "February"),
    (3, "March"),
    (4, "April"),
    (5, "May"),
    (6, "June"),
    (7, "July"),
    (8, "August"),
    (9, "September"),
    (10, "October"),
    (11, "December"),
    (12, "November"),
)

MEDIA_TYPES = (
    (1, "image"),
    (2, "video"),
    (3, "audio")
)
DATE_TYPES = (
    (0, "single"),
    (1, "period"),
)
LOCATION_TYPES = (
    (0, "separate"),
    (1, "path")
)


class PointLocation(models.Model):
    location_name = models.CharField(max_length=30, null=False, blank=False)
    location_coordinate_latitude = models.CharField(max_length=30, null=False, blank=False)
    location_coordinate_longitude = models.CharField(max_length=30, null=False, blank=False)


class Location(models.Model):
    location_type = models.IntegerField(choices=LOCATION_TYPES)
    location_list = models.ManyToManyField(PointLocation, blank=True)


class PointMentionedTime(models.Model):
    date_type = models.IntegerField(choices=DATE_TYPES, null=True, blank=True)
    date_format = models.CharField(max_length=10)
    date_string1 = models.CharField(max_length=50)
    date_string2 = models.CharField(max_length=50, null=True, blank=True)


class MemoryComment(models.Model):
    owner = models.ForeignKey(loginmodels.RegisteredUser, on_delete=models.CASCADE)
    comment = models.CharField(max_length=500, null=False, blank=False)
    comment_time = models.DateTimeField(auto_now_add=True, editable=False)


class Memory(models.Model):
    owner = models.ForeignKey(loginmodels.RegisteredUser, on_delete=models.CASCADE, related_name="owner")
    posting_time = models.DateTimeField(auto_now_add=True, editable=False)
    title = models.CharField(max_length=50, null=True, blank=True)
    comments = models.ManyToManyField(MemoryComment, blank=True)
    numlikes = models.IntegerField(default=0, null=True, blank=True)
    location = models.ForeignKey(Location, on_delete=models.CASCADE, related_name="location", null=True, blank=True)
    mentioned_time = models.ForeignKey(PointMentionedTime, null=True, blank=True, on_delete=models.CASCADE)
    liked_users = models.ManyToManyField(loginmodels.RegisteredUser, related_name="liked_user", blank=True)


class MemoryTag(models.Model):
    memory = models.ForeignKey(Memory, on_delete=models.CASCADE)
    tag = models.CharField(max_length=50, null=False, blank=False)

    def __str__(self):
        return self.memory.id.__str__() + " " + self.tag.__str__()


class MemoryMultimediaUpload(models.Model):
    media = models.FileField(upload_to='memory_image')
    media_type = models.IntegerField(choices=MEDIA_TYPES, blank=False, null=False)


class MemoryItemText(models.Model):
    memory = models.ForeignKey(Memory, on_delete=models.CASCADE)
    text = models.CharField(max_length=1000000000)
    order = models.PositiveIntegerField()


class MemoryItemMultimedia(models.Model):
    memory = models.ForeignKey(Memory, on_delete=models.CASCADE)
    multimedia = models.ForeignKey(MemoryMultimediaUpload, on_delete=models.CASCADE)
    media_type = models.IntegerField(choices=MEDIA_TYPES, blank=False, null=False)
    order = models.PositiveIntegerField()
