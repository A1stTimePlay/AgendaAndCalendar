package bss.intern.planb.Presenter.Home;

import java.util.List;

import bss.intern.planb.Database.AgendaEvent;
import bss.intern.planb.Database.AgendaEventDao;
import bss.intern.planb.View.Home.IView;

public class Presenter implements IPresenter {
    private IView view;
    private AgendaEventDao agendaEventDao;

    public Presenter(IView view, AgendaEventDao agendaEventDao) {
        this.view = view;
        this.agendaEventDao = agendaEventDao;
    }

    @Override
    public void loadAll() {
        List<AgendaEvent> agendaEventList = agendaEventDao.getAll();
        view.populateList(agendaEventList);
    }
}
