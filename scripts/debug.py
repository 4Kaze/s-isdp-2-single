import pexpect
import sys

java = pexpect.spawn("java -Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8080 -classpath ../target/classes pl/lodz/p/it/isdp/Start 10")
java.expect(['8080'])

debugger = pexpect.spawn("jdb -connect com.sun.jdi.SocketAttach:port=8080 -sourcepath src/main/java/", encoding='utf-8')
debugger.logfile_read = sys.stdout
debugger.expect_exact(["main[1] "], timeout=None)

def execute(command):
    debugger.sendline(command)
    debugger.expect_exact(["main[1] "], timeout=None)

with open(sys.argv[1], 'r') as file:
    for line in file:
        execute(line.strip('\n'))

debugger.sendline("exit")
debugger.expect(pexpect.EOF)
