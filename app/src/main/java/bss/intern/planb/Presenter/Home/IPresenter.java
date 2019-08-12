package bss.intern.planb.Presenter.Home;

import bss.intern.planb.Database.AgendaEvent;

public interface IPresenter {
    void loadAll();
    void quickCreate(AgendaEvent agendaEvent);
}
