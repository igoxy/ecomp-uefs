module controlOutputAL(button, AC, AL);
input button, AC;
output AL;


and(AL,button, !AC);
endmodule