package com.phonepe.machinecoding.repository;

import com.phonepe.machinecoding.exception.BranchAlreadyExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BranchRepositoryTest {
    private BranchRepository branchRepository = new BranchRepository();
    @Test
    public void addNewBranch_shouldReturnSuccess(){
        branchRepository.addBranch("Vasanth Vihar");
        branchRepository.addBranch("Cyber City");
        Set<String> branches = branchRepository.getBranches();
        Assertions.assertEquals(2, branches.size());
        Assertions.assertTrue(branches.contains("Vasanth Vihar"));
        Assertions.assertTrue(branches.contains("Cyber City"));
    }

    @Test
    public void addExistingBranch_shouldThrowException(){
        try {
            branchRepository.addBranch("Vasanth Vihar");
            branchRepository.addBranch("Cyber City");
            branchRepository.addBranch("Cyber City");
        }catch(Exception e){
            Assertions.assertTrue(e.getClass() == BranchAlreadyExistsException.class);
        }
    }
}
