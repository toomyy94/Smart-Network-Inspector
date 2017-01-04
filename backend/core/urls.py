from django.conf.urls import include, url
from views import index, about, list_entries, entries_history, google_url_validation


urlpatterns = [
    # ex: /
    url(r'^$', index, name='index'),
    # ex: /about/
    url(r'^about/$', about, name='about'),
    # ex: /entries/details/
    url(r'^entries/details/$', list_entries, name='entries'),
    # ex: /entries/history/
    url(r'^entries/history/$', entries_history, name='entries_history'),

    # google url validation
    url(r'^googleff1931c407ddd6d6.html', google_url_validation),
]
