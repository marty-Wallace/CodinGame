i,j,h,x=input,range,int,lambda:map(h,i().split())
n,w,r,z,y,*t,m=x()
e=[(z,y)]
for _ in'x'*m:f,p=x();e.append((f,p))
while 1:f,p,d=i().split();v=j(0,h(p)+1)if d[0]=='L'else j(h(p),w+1);print('BWLAOICTK'[any([(h(f),u)in e for u in v])::2])