""" import time
from django.test import LiveServerTestCase
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait

class UiTests(LiveServerTestCase):
    def test_navbar_navigation(self):
        driver = webdriver.Chrome()
        driver.maximize_window()

        try:
            driver.get('http://localhost:4200')

            # elements on navbar can't be clicked with click() method so it clicks them executing a js code snippet
            catalog = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", catalog)
            time.sleep(2)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(2)

            login_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", login_button)
            time.sleep(2)

            signup_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[2]/a')
            driver.execute_script("arguments[0].click();", signup_button)
            time.sleep(2)

            contact = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", contact)
            time.sleep(2)

            logo = driver.find_element(By.XPATH, '//*[@id="logoHeader"]')
            driver.execute_script("arguments[0].click()", logo)
            time.sleep(2)
        finally:
            driver.quit()

    def test_hamburguer_navigation(self):
        driver = webdriver.Chrome()

        try:
            driver.get('http://localhost:4200')

            hamb_menu = driver.find_element(By.CLASS_NAME, 'navbar-toggler-icon').click()
            catalog = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", catalog)
            time.sleep(2)

            ddown_menu = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul')
            driver.execute_script("arguments[0].click();", ddown_menu)
            time.sleep(2)

            login_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[1]/a')
            driver.execute_script("arguments[0].click();", login_button)
            time.sleep(2)

            signup_button = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[2]/ul/li[2]/a')
            driver.execute_script("arguments[0].click();", signup_button)
            time.sleep(2)

            contact = driver.find_element(By.XPATH, '//*[@id="navegacion"]/ul/li[3]/a')
            driver.execute_script("arguments[0].click();", contact)
            time.sleep(2)

            logo = driver.find_element(By.XPATH, '//*[@id="logoHeader"]')
            driver.execute_script("arguments[0].click()", logo)

            hamb_menu = driver.find_element(By.CLASS_NAME, 'navbar-toggler-icon').click()
            time.sleep(2)
        finally:
            driver.quit()
 """
