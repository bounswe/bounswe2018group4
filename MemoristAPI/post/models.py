from django.db import models
from login import models as loginmodels
from location_field.models.plain import PlainLocationField

DECADE = (
    (1, "10s"),
    (2, "20s"),
    (3, "30s"),
    (4, "40s"),
    (5, "50s"),
    (6, "60s"),
    (7, "70s"),
    (8, "80s"),
    (9, "90s"),
    (0, "00s"),
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
DAYS = (
    (1, "Monday"),
    (2, "Tuesday"),
    (3, "Wednesday"),
    (4, "Thursday"),
    (5, "Friday"),
    (6, "Saturday"),
    (7, "Sunday"),
)
MEDIA_TYPES = (
    (1, "image"),
    (2, "video"),
    (3, "audio")
)


class PointLocation(models.Model):
    location = models.CharField(max_length=30, null=False, blank=False)
    location_coordinates = PlainLocationField(based_fields=['city'], null=True, blank=True)


class PathLocation(models.Model):
    location_from = models.ForeignKey(PointLocation, related_name="location_from", on_delete=models.CASCADE)
    location_to = models.ForeignKey(PointLocation, related_name="location_to", on_delete=models.CASCADE)


class PointMentionedTime(models.Model):
    century = models.IntegerField(null=True, blank=True)
    decade = models.IntegerField(choices=DECADE, null=True, blank=True)
    year = models.IntegerField(null=True, blank=True)
    month = models.IntegerField(choices=MONTH, null=True, blank=True)
    day = models.IntegerField(null=True, blank=True)
    hour = models.IntegerField(null=True, blank=True)
    minute = models.IntegerField(null=True, blank=True)
    day_name = models.IntegerField(choices=DAYS, null=True, blank=True)


class MentionedTimePeriod(models.Model):
    time_begin = models.ForeignKey(PointMentionedTime, related_name="begin", on_delete=models.CASCADE)
    time_end = models.ForeignKey(PointMentionedTime, related_name="end", on_delete=models.CASCADE)


class MemoryComment(models.Model):
    owner = models.ForeignKey(loginmodels.RegisteredUser, on_delete=models.CASCADE)
    comment = models.CharField(max_length=500, null=False, blank=False)


class Memory(models.Model):
    owner = models.ForeignKey(loginmodels.RegisteredUser, on_delete=models.CASCADE, related_name="owner")
    posting_time = models.DateTimeField(auto_now_add=True, editable=False)
    title = models.CharField(max_length=50, null=True, blank=True)
    comments = models.ManyToManyField(MemoryComment, blank=True)
    numlikes = models.IntegerField(default=0, null=True, blank=True)
    pointlocations = models.ManyToManyField(PointLocation, blank=True)
    pathlocations = models.ManyToManyField(PathLocation, blank=True)
    mentioned_time = models.ManyToManyField(PointMentionedTime, blank=True)
    mentioned_time_period = models.ManyToManyField(MentionedTimePeriod, blank=True)
    liked_users = models.ManyToManyField(loginmodels.RegisteredUser, related_name="liked_user", blank=True)


class MemoryTag(models.Model):
    memory = models.ForeignKey(Memory, on_delete=models.CASCADE)
    tag = models.CharField(max_length=50, null=False, blank=False)


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
