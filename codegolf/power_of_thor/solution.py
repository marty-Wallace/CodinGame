a,b,c,d=map(int,input().split())
s=['SN'[b<d]]*abs(d-b)
e=['EW'[a<c]]*abs(a-c)
z=lambda x:x and x.pop()or''
while 1:print(z(s)+z(e))