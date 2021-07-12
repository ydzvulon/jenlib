import gitlab

# private token or personal token authentication
gl = gitlab.Gitlab('http://localhost:33080',
private_token='zunNra1UfNtxfqggc9mu')

gl.projects.list()

group = gl.groups.create({
    'name': 'jenlib_sample',
    'path': 'jenlib_sample'
})

group.visibility='public'
group.save()

project = gl.projects.create({
    'name': 'jenlib_sample_name',
    'visibility': 'public',
    'namespace_id': group.id
})

gl.projects.list()

fine_name = 'build.pipe.groovy'
file_content ="""
node {
    echo 'hello'
    sh 'echo world'
    stage('fetch'){
        def scmvars = checkout scm
    }
}
"""
                        #   'encoding': 'text',

f = project.files.create({'file_path': fine_name,
                          'branch': 'master',
                          'content': file_content,
                          'author_email': 'test@example.com',
                          'author_name': 'yourname',
                          'commit_message': 'Create testfile'})