package repository;

import domain.Bean;
import exception.NotFoundBeanException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanRepository {

    private int sequence = 1;
    Map<Integer, Bean> storage = new HashMap<>();

    public BeanRepository() {
        storage.put(0, new Bean(0, "노아 원두", 0, Integer.MAX_VALUE));
    }

    public List<Bean> findAllBean() {
        return new ArrayList<>(storage.values());
    }

    public void saveBean(String name, int amount, int currentTurn) {
        Bean newBean = new Bean(sequence, name, amount, currentTurn + 2);

        storage.put(sequence, newBean);

        sequence++;
    }

    public void updateNoahBean(int amount) {
        storage.get(0).addAmount(amount);
    }

    public String useBeanById(int id) throws NotFoundBeanException {
        if (!storage.containsKey(id)) {
            throw new NotFoundBeanException();
        }

        storage.get(id).useBean();

        String beanName = storage.get(id).getName();

        // 후처리 하지 않기 위해 적용
        if (storage.get(id).getAmount() == 0) {
            storage.remove(id);
        }

        return beanName;
    }

    public Bean deleteBean(int id) {
        return storage.remove(id);
    }
}
