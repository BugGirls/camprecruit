// amaze ui 选择日期

var nowTemp = new Date();
    var nowDay = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), nowTemp.getDate(), 0, 0, 0, 0).valueOf();
    var nowMoth = new Date(nowTemp.getFullYear(), nowTemp.getMonth(), 1, 0, 0, 0, 0).valueOf();
    var nowYear = new Date(nowTemp.getFullYear(), 0, 1, 0, 0, 0, 0).valueOf();
    var $myStart2 = $('#my-start-2');

    var checkin = $myStart2.datepicker({
      onRender: function(date, viewMode) {
        // days
        var viewDate = nowDay;

        switch (viewMode) {
          // moths
          case 1:
            viewDate = nowMoth;
            break;
          // years
          case 2:
            viewDate = nowYear;
            break;
        }

        return date.valueOf() < viewDate ? 'am-disabled' : '';
      }
    }).on('changeDate.datepicker.amui', function(ev) {
        if (ev.date.valueOf() > checkout.date.valueOf()) {
          var newDate = new Date(ev.date)
          newDate.setDate(newDate.getDate() + 1);
          checkout.setValue(newDate);
        }
        checkin.close();
        $('#my-end-2')[0].focus();
    }).data('amui.datepicker');

    var checkout = $('#my-end-2').datepicker({
      onRender: function(date, viewMode) {
        var inTime = checkin.date;
        var inDay = inTime.valueOf();
        var inMoth = new Date(inTime.getFullYear(), inTime.getMonth(), 1, 0, 0, 0, 0).valueOf();
        var inYear = new Date(inTime.getFullYear(), 0, 1, 0, 0, 0, 0).valueOf();

        // days
        var viewDate = inDay;

        switch (viewMode) {
          // moths
          case 1:
            viewDate = inMoth;
            break;
          // years
          case 2:
            viewDate = inYear;
            break;
        }

        return date.valueOf() <= viewDate ? 'am-disabled' : '';
      }
    }).on('changeDate.datepicker.amui', function(ev) {
      checkout.close();
    }).data('amui.datepicker');