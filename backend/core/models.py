from __future__ import unicode_literals
from django.db.models.signals import post_save
from django.dispatch import receiver
from rest_framework.authtoken.models import Token
from django.db import models
from django.contrib.auth.models import User


class InfoStorage(models.Model):
    lat = models.CharField(max_length=15)
    lon = models.CharField(max_length=15)
    info = models.CharField(max_length=500)
    msg_time = models.DateTimeField(auto_now_add=True)

    def __unicode__(self):
        return '{0} - Location: {1},{2} Timestamp: {3}'.format(self.pk, self.lat, self.lon, self.msg_time)


@receiver(post_save, sender=User)
def create_auth_token(sender, instance=None, created=False, **kwargs):
    if created:
        Token.objects.create(user=instance)
