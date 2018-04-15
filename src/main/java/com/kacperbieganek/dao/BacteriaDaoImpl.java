package com.kacperbieganek.dao;

import com.kacperbieganek.model.Examined;
import com.kacperbieganek.model.Flagella;
import com.kacperbieganek.model.Toughness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BacteriaDaoImpl implements BacteriaDao {

    private final Connection con;
    private static final Logger LOG = LoggerFactory.getLogger(BacteriaDaoImpl.class);

    public BacteriaDaoImpl(Connection con) {
        this.con = con;
    }

    @Override
    public List<Examined> getAllExamined() {
        List<Examined> list = new ArrayList<>();
        String query = "SELECT genotype,class FROM EXAMINED";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(new Examined(rs.getString("genotype"), rs.getString("class")));
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }

        return list;
    }

    @Override
    public Examined getExamined(String genotype) {
        Examined examined = null;
        String query = "SELECT class FROM EXAMINED WHERE GENOTYPE = ?";
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            pStatement.setString(1, genotype);
            ResultSet rs = pStatement.executeQuery();
            rs.next();
            examined = new Examined(genotype, rs.getString("class"));

        } catch (SQLException e) {
            LOG.error("", e);
        }
        return examined;
    }

    @Override
    public boolean insertExamined(Examined examined) {
        String query = "INSERT INTO EXAMINED (genotype,class) VALUES (?,?)";
        boolean succeeded = false;
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            pStatement.setString(1, examined.getBacteriaGenotype());
            pStatement.setString(2, examined.getBacteriaClass());
            succeeded = pStatement.executeUpdate() > 0;
            if (succeeded) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            LOG.error("", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("", e1);
            }
        }
        return succeeded;
    }

    @Override
    public boolean insertAllExamined(List<Examined> examinedList) {
        String query = "INSERT INTO EXAMINED (genotype,class) VALUES (?,?)";
        boolean succeeded = false;
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            examinedList.forEach(examined -> {
                try {
                    pStatement.setString(1, examined.getBacteriaGenotype());
                    pStatement.setString(2, examined.getBacteriaClass());
                    pStatement.addBatch();
                } catch (SQLException e) {
                    LOG.error("", e);
                }
            });
            int[] resultArray = pStatement.executeBatch();
            succeeded = Arrays.stream(resultArray).noneMatch(number -> number < 0);
            if (succeeded) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            LOG.error("", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("", e1);
            }
        }
        return succeeded;
    }

    @Override
    public List<Flagella> getAllFlagella() {
        List<Flagella> list = new ArrayList<>();
        String query = "SELECT alpha,beta,number FROM FLAGELLA";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(new Flagella(rs.getInt("alpha"), rs.getInt("beta"), rs.getInt("number")));
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return list;
    }

    @Override
    public Flagella getFlagella(int alphaGene, int betaGene) {
        Flagella flagella = null;
        String query = "SELECT NUMBER FROM FLAGELLA WHERE BETA = ? AND ALPHA = ?";
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            pStatement.setInt(1, betaGene);
            pStatement.setInt(2,alphaGene);
            ResultSet rs = pStatement.executeQuery();
            rs.next();
            flagella = new Flagella(alphaGene,betaGene, rs.getInt("NUMBER"));

        } catch (SQLException e) {
            LOG.error("", e);
        }
        return flagella;
    }

    @Override
    public boolean insertFlagella(Flagella flagella) {
        String query = "INSERT INTO FLAGELLA (alpha,beta,number) VALUES (?,?,?)";
        boolean succeeded = false;
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            pStatement.setInt(1, flagella.getAlphaGene());
            pStatement.setInt(2, flagella.getBetaGene());
            pStatement.setInt(3, flagella.getNumber());
            succeeded = pStatement.executeUpdate() > 0;
            if (succeeded) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            LOG.error("", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("", e1);
            }
        }
        return succeeded;
    }

    @Override
    public boolean insertAllFlagella(List<Flagella> flagellaList) {
        String query = "INSERT INTO FLAGELLA (alpha,beta,number) VALUES (?,?,?)";
        boolean succeeded = false;
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            flagellaList.forEach(flagella -> {
                try {
                    pStatement.setInt(1, flagella.getAlphaGene());
                    pStatement.setInt(2, flagella.getBetaGene());
                    pStatement.setInt(3, flagella.getNumber());
                    pStatement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int[] resultArray = pStatement.executeBatch();
            succeeded = Arrays.stream(resultArray).noneMatch(number -> number < 0);
            if (succeeded) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            LOG.error("", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("", e1);
            }
        }
        return succeeded;
    }

    @Override
    public List<Toughness> getAllToughness() {
        List<Toughness> list = new ArrayList<>();
        String query = "SELECT beta,gamma,rank FROM TOUGHNESS";
        try (Statement statement = con.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                list.add(new Toughness(rs.getInt("alpha"), rs.getInt("beta"), rs.getString("rank")));
            }
        } catch (SQLException e) {
            LOG.error("", e);
        }
        return list;
    }

    @Override
    public Toughness getToughness(int betaGene, int gammaGene) {
        Toughness toughness = null;
        String query = "SELECT RANK FROM TOUGHNESS WHERE BETA = ? AND GAMMA = ?";
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            pStatement.setInt(1, betaGene);
            pStatement.setInt(2,gammaGene);
            ResultSet rs = pStatement.executeQuery();
            rs.next();
            toughness = new Toughness(betaGene,gammaGene, rs.getString("RANK"));

        } catch (SQLException e) {
            LOG.error("", e);
        }
        return toughness;
    }

    @Override
    public boolean insertToughness(Toughness toughness) {
        String query = "INSERT INTO TOUGHNESS (beta,gamma,rank) VALUES (?,?,?)";
        boolean succeeded = false;
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            pStatement.setInt(1, toughness.getBetaGene());
            pStatement.setInt(2, toughness.getGammaGene());
            pStatement.setString(3, toughness.getRank());
            succeeded = pStatement.executeUpdate() > 0;
            if (succeeded) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            LOG.error("", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("", e1);
            }
        }
        return succeeded;
    }

    @Override
    public boolean insertAllToughness(List<Toughness> toughnessList) {
        String query = "INSERT INTO TOUGHNESS (beta,gamma,rank) VALUES (?,?,?)";
        boolean succeeded = false;
        try (PreparedStatement pStatement = con.prepareStatement(query)) {
            toughnessList.forEach(toughness -> {
                try {
                    pStatement.setInt(1, toughness.getBetaGene());
                    pStatement.setInt(2, toughness.getGammaGene());
                    pStatement.setString(3, toughness.getRank());
                    pStatement.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            int[] resultArray = pStatement.executeBatch();
            succeeded = Arrays.stream(resultArray).noneMatch(number -> number < 0);
            if (succeeded) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            LOG.error("", e);
            try {
                con.rollback();
            } catch (SQLException e1) {
                LOG.error("", e1);
            }
        }
        return succeeded;
    }
}
