from bs4 import BeautifulSoup
import requests
import shutil

BASE_URL = 'http://www.folger.edu'

class DramaSummary:
    def __init__(self, folder, name):
        self.folder = folder
        self.name = name

def getHtml(url):
    page = requests.get(url)
    return page.text

def fetchImage(url, filename):
    print('%s -> %s' % (url, filename))
    response = requests.get(url, stream=True)
    with open(filename, 'wb') as outfile:
        shutil.copyfileobj(response.raw, outfile)
    del response

def getBeautifulSoup(url):
    return BeautifulSoup(getHtml(url), 'lxml')

def getDramaDirList():
    url = 'http://www.folger.edu/shakespeares-works'
    soup = getBeautifulSoup(url)
    items = soup.select('ul > li > ul[class=menu] > li > a')
    res = []
    for item in items:
        folder = item.attrs['href']
        name = item.text
        res += [DramaSummary(folder, name)]
    return res

def getDramaImage(url):
    filename = url.split('/')[-1]
    soup = getBeautifulSoup(url)
    items = soup.select('img')
    items = filter(lambda item: 'jpg' in item.attrs['src'], items)
    urls = map(lambda item: item.attrs['src'], items)
    for i, u in enumerate(urls):
        fetchImage(u, filename + '-' + str(i) + '.jpg')

def main():
    dramaSummaryList = getDramaDirList()
    print("Works(%s)" % len(dramaSummaryList))
    for dramaSummary in dramaSummaryList:
        getDramaImage(BASE_URL + dramaSummary.folder)
main()
    
