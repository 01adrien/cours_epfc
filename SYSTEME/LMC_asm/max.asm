
;; mult(a,b)
	
        LDA a
        BRZ end
        LDA b
        BRZ end
        SUB one
        STA b
loop    LDA res
        ADD a
        STA res
        LDA b
        SUB one
        STA b
        BRP loop
end     LDA res
        OUT 
        HLT
a       DAT 0
b       DAT 5
one     DAT 1 
res     DAT 0