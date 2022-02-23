package com.phonepe.machinecoding.repository;

import com.phonepe.machinecoding.exception.BranchAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class BranchRepository {
    private final Set<String> branches;

    @Autowired
    public BranchRepository() {
        this.branches = new HashSet<>();
    }

    public Set<String> getBranches() {
        return this.branches;
    }

    public boolean addBranch(final String branch) {
        if(!branches.add(branch)){
            throw new BranchAlreadyExistsException();
        }
        return true;
    }

    public boolean hasBranch(final String branch) {
        return branches.contains(branch);
    }
}
