module maquina3(clk, rst, x, value, tot);
	input clk, rst, x;
	input [0:7] value;
	output reg[0:7] tot;
	parameter ini = 2'b00, esperar = 2'b01, somar = 2'b10, esperar2 = 2'b11;
	reg [1:0] state;
	
	initial begin
		state <= ini;
		tot = 0;
	end
	
	always@(posedge clk, negedge rst) begin
	
		if(rst == 0) begin
			state <= ini;
			tot = 0;
		end
		
		else begin
			case(state)
			
				ini: begin
					state <= esperar;
					tot = 0;
				end
				
				esperar: begin
					if(x == 1)  state <= esperar;
					else state <= somar;
			
				end
				
				somar: begin
					state <= esperar2;
					tot = tot + value;
				end
				
				esperar2: begin
					if(x == 1) state <= esperar;
					state <= esperar2;
				end
				
			endcase
		end
		
	end
endmodule