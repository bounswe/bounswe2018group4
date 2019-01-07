def dateFormat_hour(date):
    # d = datetime.datetime.strptime(date, '%Y-%m-%d')
    if date:
        return date.strftime('%m.%d.%Y %H:%M')
    else:
        return date
