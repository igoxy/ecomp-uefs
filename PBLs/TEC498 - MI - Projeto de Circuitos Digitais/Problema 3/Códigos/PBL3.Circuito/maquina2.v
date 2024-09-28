module maquina2(clk, rst,x,y);
	input clk, x, rst;
	output reg y;
	parameter a = 2'b00, b = 2'b01, c = 2'b10,d = 2'b11;
	reg [1:0] state;
	
	initial state <= a;
	
	always @(posedge clk, posedge rst) begin
	
		if(rst) state <= a;
			
		else begin
			case (state)
				a: begin
					if(x == 1) state <= b;
					else state = a;
				end
				
				b: begin
					if(x == 0) state <= c;
					else state = b;
				end
				
				c: begin
					if(x == 1) state <= d;
					else state = a;
				end
				
				d: begin
					if(x == 1) state <= b;
					else state = c;
				end
				
				
				
			endcase
		end
	end
	
	always@(state,x) begin
		case (state)
				a: y = 0;
				
				b: y = 0;
				
				c: y = 0;
				
				d: begin
					if(x == 1) y = 0;
					else y = 0;
				end
		endcase
	end
endmodule