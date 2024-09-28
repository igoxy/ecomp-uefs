module maquina(x, clk, rst, y);
	input x,clk,rst;
	output reg y;
	parameter a = 2'b00, b = 2'b01, c = 2'b10, d = 2'b11;
	reg [1:0] state;
	
	initial state <= a;
	
	always@(posedge clk, posedge rst) begin
		if(rst) state <= a;
		else begin
		
			case (state)
				a: begin
					if(x == 0) state <= b;
					else state <= a;
				end
				
				b: begin
					if(x == 1) state <= b;
					else state <= c;
				end
				
				c: begin
					 if(x == 1) state <= d;
					else state <= a;
				end
				
				d: begin
					 if(x == 1) state <= b;
					else state <= c;
				end
			endcase
		end
		
			
	end
	
	always@(state) begin
		case (state)
			a: begin
				y = 1;
			end
			
			b: begin
				y = 0;
			end
			
			c: begin
				 y = 0;
			end
			
			d: begin
				y = 1;
			end
			
		endcase
			
			
	end
endmodule