from django.conf.urls import include, url
from views import InfoViewer, InfoAdd

urlpatterns = [
    # ex: /api/backend/info/1/
    url(r'^backend/info/(?P<pk>[0-9]+)/$', InfoViewer.as_view()),
    # ex: /api/backend/info/
    url(r'^backend/info/$', InfoAdd.as_view()),
]
