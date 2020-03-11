package com.example.businessplanner.presentation.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {
    private final int color;
    private final HashSet<Calendar> dates;

    public EventDecorator(int color, Collection<Calendar> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day.getCalendar());
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }
}