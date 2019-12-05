package uet.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.stereotype.Service;
import uet.model.GradeLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author joker
 */
@Service
public class GradeLevelService
{
    private GradeLevel gradeLevel;
    @Autowired
    private JdbcTemplate jdbcTemp;
    
    public GradeLevelService()
    {
        //
    }
    
    public GradeLevelService(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public List<GradeLevel> getAll()
    {
        List<GradeLevel> gradeLevels;
        gradeLevels = jdbcTemp.query("select * from grade_level", new RowMapper<GradeLevel>() {
            @Override
            public GradeLevel mapRow(ResultSet rs, int i) throws SQLException {
                GradeLevel gLevel = new GradeLevel();
                gLevel.setId(rs.getInt("id"));
                gLevel.setCode(rs.getString("code"));
                gLevel.setShortName(rs.getString("short_name"));
                return gLevel;
            }
        });
        return gradeLevels;
    }
}
