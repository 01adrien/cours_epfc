	LDA b
	SUB one
	STA b
loop	LDA res
	ADD a
	STA res
	LDA b
	SUB one
	STA b
	BRP loop
	LDA res
	OUT 
        HLT
a       DAT 5
b       DAT 2
one     DAT 1 
res 	DAT 0