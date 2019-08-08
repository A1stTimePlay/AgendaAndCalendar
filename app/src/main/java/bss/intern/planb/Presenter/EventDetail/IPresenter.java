package bss.intern.planb.Presenter.EventDetail;

import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.WeekView.WeekViewEvent;

public interface IPresenter {
    void getAgendaEventById(WeekViewEvent weekViewEvent);
    void deleteAgendaEvent(WeekViewEvent weekViewEvent);
    void updateAgendaEvent(WeekViewEvent weekViewEvent);
}
