input()
print(min(map(int,input().split()),key=lambda x:x*x-x/9,default=0))