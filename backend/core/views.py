from django.shortcuts import render
from django.template import RequestContext, loader
from django.shortcuts import render_to_response
from django.http import HttpResponse

from models import InfoStorage

import datetime


def index(request):
    try:
        count = InfoStorage.objects.all().count()
        num_entries = '%d entry' % count if count == 1 else '%d entries' % count
    except:
        num_entries = 'No entries'

    template = loader.get_template('core/index.html')
    context = RequestContext(request, {
        'num_entries': num_entries,
    })
    return HttpResponse(template.render(context))


def about(request):
    return render_to_response("core/about.html", RequestContext(request))


def list_entries(request):
    try:
        lst = InfoStorage.objects.all()
    except:
        lst = 'No entries'

    template = loader.get_template('core/list_entries.html')
    context = RequestContext(request, {
        'list_entries': lst,
    })
    return HttpResponse(template.render(context))


def entries_history(request):
    try:
        # lst = InfoStorage.objects.filter(msg_time__range=["2016-12-01", "2017-01-31"])
        lst = []
        ano = datetime.datetime.now().year
        start_y, prst_y = ano - 2, ano + 1  # desde menos dois anos do ano presente ate ao ano atual

        for year in xrange(start_y, prst_y):
            for month in xrange(1, 13):
                for day in xrange(1, 32):
                    data = {
                        "y": None,
                        "a": None
                    }
                    y = "%02d/%02d/%04d" % (day, month, year)

                    c = len(InfoStorage.objects.filter(msg_time__year=year, msg_time__month=month, msg_time__day=day))
                    if c > 0:
                        data['y'] = y
                        data['a'] = c
                        lst.append(data)
    except:
        lst = []

    template = loader.get_template('core/history_chart.html')
    context = RequestContext(request, {
        'entries_history': lst,
    })
    return HttpResponse(template.render(context))
