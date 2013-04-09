@GrabConfig(systemClassLoader=true)
@Grab(group='com.h2database', module='h2', version='1.3.171')

import groovy.sql.Sql

def sql = Sql.newInstance('jdbc:h2:mem:db1', 'sa', 'sa', 'org.h2.Driver')
sql.execute 'create table Person (id int, first_name varchar(50), last_name varchar(50))'
sql.execute "insert into Person values(1, 'Marvin', 'Martian')"
sql.execute "insert into Person values(2, 'Buddy', 'Lee')"

println sql.rows('select * from Person where first_name=?', 'Buddy').join('\n')

// How can we get the following to run?
// println 'select * from Person where first_name=?'.against(sql).using('Buddy')
