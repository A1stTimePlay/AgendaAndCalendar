package bss.intern.planb.Presenter.EventDetail;

import bss.intern.planb.WeekView.WeekViewEvent;

public interface IPresenter {
    void getAgendaEventById(WeekViewEvent weekViewEvent);
    void deleteAgendaEvent(WeekViewEvent weekViewEvent);
}
