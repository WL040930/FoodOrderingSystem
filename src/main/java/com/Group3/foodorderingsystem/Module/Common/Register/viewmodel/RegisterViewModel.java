package com.Group3.foodorderingsystem.Module.Common.Register.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class RegisterViewModel {
    private List<RegisterRole> roles = new ArrayList<RegisterRole>();
    private RegisterRole selectedRole; 

    public void init() {
        for (RegisterRole role : RegisterRole.values()) {
            roles.add(role);
        }

        selectedRole = roles.getFirst(); 
    }

    public List<RegisterRole> getRoles() {
        return roles;
    }

    public RegisterRole getSelectedRole() {
        return selectedRole;
    }

    public void reorderRoles(int nextOrPrevious) {
        int currentIndex = roles.indexOf(selectedRole);
        int newIndex = (currentIndex + nextOrPrevious + roles.size()) % roles.size();
        selectedRole = roles.get(newIndex);
    }
}
