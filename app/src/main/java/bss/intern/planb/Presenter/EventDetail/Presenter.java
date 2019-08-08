package bss.intern.planb.Presenter.EventDetail;

import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Database.AgendaEventDao;
import bss.intern.planb.View.AgendaDetail.View;
import bss.intern.planb.WeekView.WeekViewEvent;

public class Presenter implements IPresenter {

    private View view;
    private AgendaEventDao agendaEventDao;

    public Presenter(View view, AgendaEventDao agendaEventDao) {
        this.view = view;
        this.agendaEventDao = agendaEventDao;
    }

    @Override
    public void getAgendaEventById(WeekViewEvent weekViewEvent) {
        AgendaEvent temp = agendaEventDao.findById((int)weekViewEvent.getId());
        view.displayAgendaEventDetail(temp);
    }

    @Override
    public void deleteAgendaEvent(WeekViewEvent weekViewEvent) {
        AgendaEvent temp = agendaEventDao.findById((int)weekViewEvent.getId());
        agendaEventDao.deleteAgenda(temp);
        view.successfulDetele();
    }

    @Override
    public void updateAgendaEvent(WeekViewEvent weekViewEvent) {
//        AgendaEvent temp = agendaEventDao.findById((int)weekViewEvent.getId());
    }
}
