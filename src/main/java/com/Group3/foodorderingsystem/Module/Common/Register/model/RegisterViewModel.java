package com.Group3.foodorderingsystem.Module.Common.Register.model;

import java.util.ArrayList;
import java.util.List;

public class RegisterViewModel {
    public static RegisterViewModel instance;
    private List<RegisterRole> roles = new ArrayList<>();
    private RegisterRole selectedRole; 

    public void init() {
        for (RegisterRole role : RegisterRole.values()) {
            roles.add(role);
        }

        if (!roles.isEmpty()) {
            selectedRole = roles.get(0);
        }
    }

    public RegisterRole getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(RegisterRole role) {
        selectedRole = role;
    }

    public void reorderRoles(int nextOrPrevious) {
        int currentIndex = roles.indexOf(selectedRole);
        int newIndex = (currentIndex + nextOrPrevious + roles.size()) % roles.size();
        selectedRole = roles.get(newIndex);
    }
}
