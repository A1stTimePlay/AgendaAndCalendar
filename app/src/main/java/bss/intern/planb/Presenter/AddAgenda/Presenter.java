package bss.intern.planb.Presenter.AddAgenda;

import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Database.AgendaEventDao;
import bss.intern.planb.View.AddAndEditEvent.View;

public class Presenter implements IPresenter {

    private View view;
    private AgendaEventDao agendaEventDao;

    public Presenter(View view, AgendaEventDao agendaEventDao) {
        this.view = view;
        this.agendaEventDao = agendaEventDao;
    }

    @Override
    public void createAgenda(AgendaEvent agendaEvent) {
        agendaEventDao.insertAgenda(agendaEvent);
        view.successful();
    }

    @Override
    public void editAgenda(AgendaEvent agendaEvent) {
        agendaEventDao.updateAgenda(agendaEvent);
        view.successful();
    }
}
