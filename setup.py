from setuptools import setup, find_packages

setup(
    name="nova-act",
    version="1.0.0",
    packages=find_packages(where="src/main/python"),
    package_dir={"": "src/main/python"},
    install_requires=[
        "numpy>=1.21.0",
        "pandas>=1.3.0"
    ],
    python_requires=">=3.9",
    test_suite="src.test.python"
)
