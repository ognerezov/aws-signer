package net.okhotnikov.aws_signer.api;

import java.util.List;

public class ListHolder<T> {

    public ListHolder() {
    }

    public ListHolder(List<T> list) {
        this.list = list;
    }

    public List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
