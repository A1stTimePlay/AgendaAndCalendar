package bss.intern.planb.Presenter.Home;

import bss.intern.planb.Database.AgendaEventDao;
import bss.intern.planb.View.Home.IView;

public class Presenter implements IPresenter {
    private IView view;
    private AgendaEventDao agendaEventDao;

    @Override
    public void loadAll() {
        
//        view.addEventToListOnView();
    }
}
