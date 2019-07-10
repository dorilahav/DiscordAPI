package com.dorilahav.api.commands;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class Flags {

    @Getter
    @Setter
    private List<String>
            flags;

    public Flags add(String flag) {
        this.flags.add(flag.toLowerCase());
        return this;
    }

    public boolean contains(String flag) {
        return this.flags.contains(flag.toLowerCase());
    }
}
